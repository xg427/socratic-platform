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

import de.atb.socratic.model.Campaign;
import de.atb.socratic.model.notification.NewCampaignNotification;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.PanelMarkupSourcingStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by spindler on 05.08.2016.
 */
public class NewCampaignNotificationLink extends NotificationBookmarkablePageLink<NewCampaignNotification> {

    /**
     *
     */
    private static final long serialVersionUID = 6277856095704051161L;


    public NewCampaignNotificationLink(String id, IModel<NewCampaignNotification> model) {
        this(id, new PageParameters(), model);
    }

    public NewCampaignNotificationLink(String id, PageParameters parameters, IModel<NewCampaignNotification> model) {
        super(id, getTargetPageClassForCampaign(model.getObject().getCampaign()), forCampaign(model.getObject().getCampaign()), model);
        add(new Label("campaignCreator", getCampaign().getCreatedBy().getNickName()));
        add(new Label("campaignName", getCampaign().getName()));
    }

    public Campaign getCampaign() {
        return getModelObject() != null ? getModelObject().getCampaign() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
        return new PanelMarkupSourcingStrategy(true);
    }
}
