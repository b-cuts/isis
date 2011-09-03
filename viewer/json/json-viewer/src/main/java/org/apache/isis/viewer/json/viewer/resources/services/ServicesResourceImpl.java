/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.viewer.json.viewer.resources.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.services.ServiceUtil;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.viewer.json.applib.domain.ServicesResource;
import org.apache.isis.viewer.json.viewer.RepContext;
import org.apache.isis.viewer.json.viewer.resources.ResourceAbstract;
import org.apache.isis.viewer.json.viewer.resources.objectlist.DomainObjectListRepBuilder;
import org.apache.isis.viewer.json.viewer.resources.objectlist.DomainServiceListRepBuilder;
import org.apache.isis.viewer.json.viewer.resources.objects.DomainObjectRepBuilder;

public class ServicesResourceImpl extends ResourceAbstract implements ServicesResource {

    @Override
    @Produces({ MediaType.APPLICATION_JSON })
    public Response services() {
        init();

        final List<ObjectAdapter> serviceAdapters = getPersistenceSession().getServices();
		RepContext repContext = getResourceContext().repContext();

        DomainServiceListRepBuilder builder = DomainServiceListRepBuilder.newBuilder(repContext, serviceAdapters);
        return responseOfOk(jsonRepresentionFrom(builder));
    }

    @GET
    @Path("/{serviceId}")
    @Produces({ MediaType.APPLICATION_JSON })
    @Override
    public Response service(@PathParam("serviceId") String serviceId) {
        init();

        final ObjectAdapter serviceAdapter = getServiceAdapter(serviceId);
        if(serviceAdapter == null) {
            return responseOfNotFound("Could not locate service '%s'", serviceId);
        }
        RepContext repContext = getResourceContext().repContext();

        DomainObjectRepBuilder builder = DomainObjectRepBuilder.newBuilder(repContext, serviceAdapter);
        return responseOfOk(jsonRepresentionFrom(builder));
    }


    private ObjectAdapter getServiceAdapter(String serviceId) {
        final List<ObjectAdapter> serviceAdapters = getPersistenceSession().getServices();
        for (ObjectAdapter serviceAdapter : serviceAdapters) {
            Object servicePojo = serviceAdapter.getObject();
            String id = ServiceUtil.id(servicePojo);
            if(serviceId.equals(id)) {
                return serviceAdapter;
            }
        }
        return null;
    }

}