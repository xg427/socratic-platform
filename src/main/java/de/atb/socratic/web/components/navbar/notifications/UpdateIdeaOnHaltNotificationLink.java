package de.atb.socratic.web.components.navbar.notifications;

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

import de.atb.socratic.model.Idea;
import de.atb.socratic.model.notification.IdeaOnHaltNotification;
import de.atb.socratic.web.inception.idea.details.IdeaDetailsPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.PanelMarkupSourcingStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author ATB
 */
public class UpdateIdeaOnHaltNotificationLink extends NotificationBookmarkablePageLink<IdeaOnHaltNotification> {

    private static final long serialVersionUID = -6847965169284935767L;

    public UpdateIdeaOnHaltNotificationLink(String id, IModel<IdeaOnHaltNotification> model) {
        this(id, new PageParameters(), model);
    }

    public UpdateIdeaOnHaltNotificationLink(String id, PageParameters parameters, IModel<IdeaOnHaltNotification> model) {
        super(id, IdeaDetailsPage.class, (parameters == null ? new PageParameters().set("ideaId",
                model.getObject().getIdea().getId()).set("id", model.getObject().getIdea().getCampaign().getId()) : parameters.add(
                "ideaId", model.getObject().getIdea().getId()).add("id", model.getObject().getIdea().getCampaign().getId())), model);

        add(new Label("ideaName", getIdea().getShortText())); // get idea name
    }

    public Idea getIdea() {
        return getModelObject() != null ? getModelObject().getIdea() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
        return new PanelMarkupSourcingStrategy(true);
    }
}
