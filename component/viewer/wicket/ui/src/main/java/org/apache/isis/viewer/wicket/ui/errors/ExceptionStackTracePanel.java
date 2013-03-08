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

package org.apache.isis.viewer.wicket.ui.errors;

import org.apache.isis.viewer.wicket.ui.panels.PanelUtil;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class ExceptionStackTracePanel extends Panel {

    private static final long serialVersionUID = 1L;

    private static final String ID_MAIN_MESSAGE = "mainMessage";

    private static final String ID_EXCEPTION_DETAIL = "exceptionDetail";

    private static final String ID_STACK_TRACE_ELEMENT = "stackTraceElement";
    private static final String ID_LINE = "stackTraceElementLine";

    private static final JavaScriptResourceReference DIV_TOGGLE_JS = new JavaScriptResourceReference(ExceptionStackTracePanel.class, "div-toggle.js");

    public ExceptionStackTracePanel(String id, ExceptionModel exceptionModel) {
        super(id, exceptionModel);
        
        add(new Label(ID_MAIN_MESSAGE, exceptionModel.getMainMessage()).setEscapeModelStrings(false));

        MarkupContainer container = new WebMarkupContainer(ID_EXCEPTION_DETAIL) {
            private static final long serialVersionUID = 1L;
            @Override
            public void renderHead(IHeaderResponse response) {
                response.render(JavaScriptReferenceHeaderItem.forReference(DIV_TOGGLE_JS));
            }
        };
        container.add(new StackTraceListView(ID_STACK_TRACE_ELEMENT, ExceptionStackTracePanel.ID_LINE, exceptionModel.getStackTrace()));
        add(container);
    }

    public void renderHead(final IHeaderResponse response) {
        PanelUtil.renderHead(response, this.getClass());
    }

}
