/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mec.meo.event;

import java.util.Map;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.model.EventMessage;
import com.ubiqube.etsi.mano.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.ActionMessage;
import com.ubiqube.etsi.mano.service.event.ActionType;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class MeoEventManagerImpl implements MeoEventManager {
	private final JmsTemplate jmsTemplate;

	public MeoEventManagerImpl(final JmsTemplate jmsTemplate) {
		super();
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void sendMeoEvent(final @NotNull ActionType actionType, final UUID objectId, final Map<String, Object> parameters) {
		final ActionMessage msg = new ActionMessage(actionType, objectId, parameters);
		jmsTemplate.convertAndSend("system.actions.meo", msg);
	}

	@Override
	public void sendNotification(final NotificationEvent notificationEvent, final UUID objectId) {
		final EventMessage msg = new EventMessage(notificationEvent, objectId, Map.of());
		jmsTemplate.convertAndSend("system.notifications", msg);
	}


}
