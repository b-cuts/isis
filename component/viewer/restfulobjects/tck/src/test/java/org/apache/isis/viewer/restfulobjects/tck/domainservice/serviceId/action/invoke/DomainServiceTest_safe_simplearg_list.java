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
package org.apache.isis.viewer.restfulobjects.tck.domainservice.serviceId.action.invoke;

import static org.apache.isis.viewer.restfulobjects.tck.RepresentationMatchers.isLink;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.LinkRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.Rel;
import org.apache.isis.viewer.restfulobjects.applib.RestfulHttpMethod;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulClient;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.ActionResultRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.ActionResultRepresentation.ResultType;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.DomainServiceResource;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.ListRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.ObjectActionRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.util.UrlEncodingUtils;
import org.apache.isis.viewer.restfulobjects.tck.IsisWebServerRule;
import org.apache.isis.viewer.restfulobjects.tck.RepresentationMatchers;
import org.apache.isis.viewer.restfulobjects.tck.Util;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class DomainServiceTest_safe_simplearg_list {

    @Rule
    public IsisWebServerRule webServerRule = new IsisWebServerRule();

    private RestfulClient client;

    private DomainServiceResource serviceResource;

    @Before
    public void setUp() throws Exception {
        client = webServerRule.getClient();

        serviceResource = client.getDomainServiceResource();
    }

    @Test
    public void invokeQueryOnly_noArg_usingClientFollow_returning_list() throws Exception {

        // given
        final JsonRepresentation givenAction = Util.givenAction(client, "ActionsEntities", "subList");
        final ObjectActionRepresentation actionRepr = givenAction.as(ObjectActionRepresentation.class);

        final LinkRepresentation invokeLink = actionRepr.getInvoke();

        assertThat(invokeLink, isLink(client)
                                    .rel(Rel.INVOKE)
                                    .httpMethod(RestfulHttpMethod.GET)
                                    .href(Matchers.endsWith(":39393/services/ActionsEntities/actions/subList/invoke"))
                                    .build());
        
        JsonRepresentation args =invokeLink.getArguments();
        assertThat(args.size(), is(2));
        assertThat(args, RepresentationMatchers.mapHas("from"));
        assertThat(args, RepresentationMatchers.mapHas("to"));
        
        // when
        args.mapPut("from", 1);
        args.mapPut("to", 3);

        final RestfulResponse<ActionResultRepresentation> restfulResponse = client.followT(invokeLink, args);
        
        // then
        final ActionResultRepresentation actionResultRepr = restfulResponse.getEntity();
        
        assertThat(actionResultRepr.getResultType(), is(ResultType.LIST));
        final ListRepresentation listRepr = actionResultRepr.getResult().as(ListRepresentation.class);
        assertThat(listRepr.getValue().size(), is(2));
    }


    @Ignore("up to here...")
    @Test
    public void invokeQueryOnly_noArg_usingResourceProxy_returning_list() throws Exception {

        // given, when
        JsonRepresentation args = JsonRepresentation.newMap();
        args.mapPut("from", 1);
        args.mapPut("to", 3);
        String xIsisQueryString = UrlEncodingUtils.urlEncode(args);
        
        Response response = serviceResource.invokeActionQueryOnly("ActionsEntities", "subList", xIsisQueryString);
        RestfulResponse<ActionResultRepresentation> restfulResponse = RestfulResponse.ofT(response);
        
        // then
        final ActionResultRepresentation actionResultRepr = restfulResponse.getEntity();
        
        assertThat(actionResultRepr.getResultType(), is(ResultType.LIST));
        
        final ListRepresentation listRepr = actionResultRepr.getResult().as(ListRepresentation.class);

        assertThat(listRepr.getValue().size(), is(2));
    }

}
