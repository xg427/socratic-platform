package de.atb.socratic.model.notification;

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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Query;
import javax.xml.bind.annotation.XmlRootElement;

import de.atb.socratic.model.Idea;
import de.atb.socratic.web.components.navbar.notifications.NotificationBookmarkablePageLink;
import de.atb.socratic.web.components.navbar.notifications.ParticipateInPrioritisationNotificationLink;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * ParticipateInPrioritisationNotification
 *
 * @author ATB
 */
@Entity
@XmlRootElement
// required as discrimator type is varchar(31) (to short!)
@DiscriminatorValue(value = "PIPN")
public class ParticipateInPrioritisationNotification extends Notification {

    private static final long serialVersionUID = -105225851072942368L;

    public ParticipateInPrioritisationNotification() {
        this.setNotificationType(NotificationType.PRIORITISATION_PARTICIPATE);
    }

    /**
     * @return an idea
     */
    public Idea getIdea() {
        return idea;
    }

    /**
     * @param idea an idea to set
     */
    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.eff.model.notification.Notification#getLink(java.lang.String,
     * org.apache.wicket.model.IModel)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <N extends Notification, P extends Page, T extends NotificationBookmarkablePageLink<N>> T getLink(String id, IModel<N> model) {
        return (T) new ParticipateInPrioritisationNotificationLink(id, (IModel<ParticipateInPrioritisationNotification>) model);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.eff.model.notification.Notification#getLink(java.lang.String,
     * org.apache.wicket.request.mapper.parameter.PageParameters,
     * org.apache.wicket.model.IModel)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <N extends Notification, P extends Page, T extends NotificationBookmarkablePageLink<N>> T getLink(String id,
                                                                                                             PageParameters parameters, IModel<N> model) {
        return (T) new ParticipateInPrioritisationNotificationLink(id, parameters, (IModel<ParticipateInPrioritisationNotification>) model);
    }

    @Override
    public void setParameters(Query q) {
        q.setParameter("obj", getIdea());
        q.setParameter("dType", "PIPN");
    }

    @Override
    public String getColumnName() {
        return "idea";
    }

}
