package org.esupportail.proto.web.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esupportail.commons.services.i18n.I18nService;
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

@Controller
@RequestMapping("VIEW")
public class HomeController {

    final String TREE_VISIBLE = "treeVisible";
    final String CHANGE_ITEM_DISPLAY_MODE = "changeItemDisplayMode";
    final String AVAILABLE_ITEM_DISPLAY_MODE = "availableItemDisplayModes";
    /**
     * Log instance.
     */
    private static final Log LOG = LogFactory.getLog(HomeController.class);

    protected static final String MESSAGES = "messages";
    final String STATICRESOURCESPATH = "resourcesPath";
    final String DYNAMICRESOURCESPATTERN = "dynamicResourcesPattern";
    final String GUEST_MODE = "guestMode";
    final String CONTEXT = "context";

    /**
     * The i18nService
     */
    @Resource(name = "i18nService")
    protected I18nService i18nService;

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
//        view.addStaticAttribute(CONTEXT, getContext());
//        view.addStaticAttribute(GUEST_MODE, isGuestMode());
//        Locale locale = request.getLocale();
//        view.addStaticAttribute(MESSAGES, i18nService.getStrings(locale));
        return view;
    }

    /**
     * action : toggle item from read to unread and unread to read.
     *
     * @param catID Category ID
     * @param srcID Source ID
     * @param itemID Item ID
     * @param isRead is source read ?
     */
    @ResourceMapping(value = "toggleItemReadState")
    public void toggleItemReadState(
            @RequestParam(required = true, value = "p1") String catID,
            @RequestParam(required = true, value = "p2") String srcID,
            @RequestParam(required = true, value = "p3") String itemID,
            @RequestParam(required = true, value = "p4") boolean isRead) {
    }

}
