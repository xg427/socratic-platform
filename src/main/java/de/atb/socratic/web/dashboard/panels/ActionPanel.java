package de.atb.socratic.web.dashboard.panels;

/*-
 * #%L
 * socratic-platform
 * %%
 * Copyright (C) 2016 - 2018 Institute for Applied Systems Technology Bremen GmbH (ATB)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import javax.ejb.EJB;
import javax.inject.Inject;

import de.atb.socratic.model.Action;
import de.atb.socratic.model.User;
import de.atb.socratic.service.implementation.ActivityService;
import de.atb.socratic.web.action.detail.ActionSolutionPage;
import de.atb.socratic.web.components.resource.PictureType;
import de.atb.socratic.web.components.resource.ProfilePictureResource;
import de.atb.socratic.web.dashboard.Dashboard.StateForDashboard;
import de.atb.socratic.web.dashboard.iLead.action.AdminActionActivityPage;
import de.atb.socratic.web.qualifier.LoggedInUser;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jboss.solder.logging.Logger;

/**
 * @author ATB
 */
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
public abstract class ActionPanel extends GenericPanel<Action> {

    /**
     *
     */
    private static final long serialVersionUID = -522989596052194414L;

    @Inject
    Logger logger;

    // inject the currently logged in user
    @Inject
    @LoggedInUser
    User loggedInUser;

    @EJB
    ActivityService activityService;

    /**
     * @param id
     * @param model
     */
    public ActionPanel(final String id, final IModel<Action> model, final StateForDashboard dashboardState) {
        super(id, model);

        setOutputMarkupId(true);

        final Action action = getModelObject();

        // add action data
        WebMarkupContainer headLine = new WebMarkupContainer("headline");
        add(headLine);

        add(new Label("ideaName", new PropertyModel<String>(action.getIdea(), "shortText")));

        headLine.add(new Label("shortText", new PropertyModel<String>(action, "shortText")));

        add(new NonCachingImage("profilePicture", ProfilePictureResource.get(PictureType.THUMBNAIL, action.getPostedBy())));

        add(new Label("postedBy.nickName", new PropertyModel<String>(action, "postedBy.nickName")));
        add(new Label("postedBy.city", new PropertyModel<String>(action, "postedBy.city")));
        add(new Label("postedBy.country", new PropertyModel<String>(action, "postedBy.country")));

        Label contributors = new Label("contributors", activityService.countAllActionContributorsBasedOnActionActivity(action));
        add(contributors.setOutputMarkupId(true));

        WebMarkupContainer wmc = new WebMarkupContainer("container") {
            private static final long serialVersionUID = -484431348509395229L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                if (StateForDashboard.TakePart == dashboardState) {
                    this.add(new AttributeModifier("class", "well"));
                }
            }
        };
        add(wmc);

        // add callToAction as help text(if dashboardState == Participate)
        wmc.add(newHelpText(action, dashboardState));
        // add explore or manage button
        wmc.add(newButton(action, dashboardState));
    }

    private Label newHelpText(final Action action, final StateForDashboard dashboardState) {
        return new Label("helpText", new PropertyModel<String>(action, "callToAction")) {
            private static final long serialVersionUID = 3779700606433655558L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                if (StateForDashboard.TakePart == dashboardState) {
                    setVisible(true);
                } else {
                    setVisible(false);
                }
            }
        };
    }

    private AjaxLink<?> newButton(final Action action, final StateForDashboard dashboardState) {
        return new AjaxLink<Void>("exploreOrManageButton") {
            private static final long serialVersionUID = -8233439456118623954L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (StateForDashboard.Lead == dashboardState && action.getPostedBy().equals(loggedInUser)) {
                    setResponsePage(AdminActionActivityPage.class, new PageParameters().set("id", action.getId()));
                } else {
                    setResponsePage(ActionSolutionPage.class, new PageParameters().set("id", action.getId()));
                }
            }

            @Override
            protected void onConfigure() {
                if (StateForDashboard.Lead == dashboardState && action.getPostedBy().equals(loggedInUser)) {
                    this.add(new Label("buttonLabel", new StringResourceModel("actions.manage.button", this, null).getString()));
                } else {
                    this.add(new Label("buttonLabel", new StringResourceModel("actions.explore.button", this, null).getString()));
                }
                setOutputMarkupId(true);
            }

            ;

        };
    }

}
