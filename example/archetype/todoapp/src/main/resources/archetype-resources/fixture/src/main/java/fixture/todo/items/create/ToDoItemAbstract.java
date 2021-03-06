#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
package fixture.todo.items.create;

import dom.todo.ToDoItem;
import dom.todo.ToDoItem.Category;
import dom.todo.ToDoItem.Subcategory;
import dom.todo.ToDoItems;

import java.math.BigDecimal;
import org.joda.time.LocalDate;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

public abstract class ToDoItemAbstract extends FixtureScript {

    protected ToDoItem createToDoItem(
            final String description,
            final Category category, final Subcategory subcategory,
            final LocalDate dueBy,
            final BigDecimal cost,
            final ExecutionContext executionContext) {

        // validate parameters
        final String ownedBy = executionContext.getParameter("ownedBy");
        if(ownedBy == null) {
            throw new IllegalArgumentException("'ownedBy' must be specified");
        }

        // execute
        ToDoItem newToDo = toDoItems.newToDo(
                description, category, subcategory, ownedBy, dueBy, cost);
        return executionContext.add(this, newToDo);
    }

    protected LocalDate nowPlusDays(int days) {
        return clockService.now().plusDays(days);
    }

    protected BigDecimal BD(String str) {
        return new BigDecimal(str);
    }

    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    protected ClockService clockService;
    //endregion


}