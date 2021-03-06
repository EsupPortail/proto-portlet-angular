package org.esupportail.proto.web.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esupportail.commons.i18n.I18nService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.annotation.Resource;
import javax.portlet.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("VIEW")
public class HomeController {

    /**
     * Log instance.
     */
    private static final Log LOG = LogFactory.getLog(HomeController.class);

    protected static final String MESSAGES = "messages";
    final String STATICRESOURCESPATH = "resourcesPath";
    final String DYNAMICRESOURCESPATTERN = "dynamicResourcesPattern";

    /**
     * The i18nService
     */
    @Resource(name = "i18nService")
    protected I18nService i18nService;

    /**
     * render home page
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RenderMapping
    public String goHome(RenderRequest request, RenderResponse response, ModelMap model) {
        model = bindInitialModel(model, response, request);
        return "home";
    }

    @ResourceMapping(value = "getJSON")
    public View getJSON(ResourceRequest request, ResourceResponse response) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Locale locale = request.getLocale();
        view.addStaticAttribute(MESSAGES, i18nService.getStrings(locale));
        view.addStaticAttribute("pref1", request.getPreferences().getValue("pref1", "pref1 not found !"));
        view.addStaticAttribute("uid", request.getAttribute("uid"));
        view.addStaticAttribute("portletMode", request.getPortletMode().toString());
        view.addStaticAttribute("windowState", request.getWindowState().toString());
        return view;
    }

    /**
     * Add ressourceURL to spring MVC model
     * @param model
     * @param response
     * @param request
     * @return completed spring MVC model
     */
    protected ModelMap bindInitialModel(ModelMap model, final RenderResponse response, final RenderRequest request) {
        model.addAttribute(STATICRESOURCESPATH, response.encodeURL(request.getContextPath()));
        ResourceURL resourceURL = response.createResourceURL();
        resourceURL.setResourceID("@@id@@");
        resourceURL.setParameter("p1", "__p1__");
        resourceURL.setParameter("p2", "__p2__");
        resourceURL.setParameter("p3", "__p3__");
        resourceURL.setParameter("p4", "__p4__");
        model.addAttribute(DYNAMICRESOURCESPATTERN, resourceURL);
        return model;
    }

    /**
     * @param name of stored object
     * @return the object stored in session
     */
    protected Object getFromSession(final String name) {
        return RequestContextHolder.getRequestAttributes().getAttribute(name, PortletSession.PORTLET_SCOPE);
    }

    /**
     * Store a object in session
     *
     * @param name of stored object
     * @param value of stored object
     */
    protected void setInSession(final String name, Object value) {
        RequestContextHolder.getRequestAttributes().setAttribute(name, value, PortletSession.PORTLET_SCOPE);
    }

    @ResourceMapping(value = "getDate")
    public View toggleItemReadState(
            @RequestParam(required = true, value = "p1") boolean isWithHour) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Date date = new Date();
        String ret;
        if (isWithHour) {
            ret = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
        }
        else {
            ret = new SimpleDateFormat("dd/MM/yyyy").format(date);
        }
        view.addStaticAttribute("date", ret);
        return view;
    }

}
