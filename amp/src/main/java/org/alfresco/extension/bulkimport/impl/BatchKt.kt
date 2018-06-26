/*
 * Copyright (C) 2007 Peter Monks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is part of an unsupported extension to Alfresco.
 *
 */

package org.alfresco.extension.bulkimport.impl

import org.alfresco.extension.bulkimport.source.BulkImportItem
import org.alfresco.extension.bulkimport.source.BulkImportItemVersion

/**
 * This class represents a single batch of items that need to be imported.
 *
 * @author Peter Monks (pmonks@gmail.com)
 *
 */
class BatchKt {

    var number: Int
    val contents: List<BulkImportItem<BulkImportItemVersion>>

    constructor(number: Int, contents: List<BulkImportItem<BulkImportItemVersion>>){

        if (number <= 0) throw IllegalArgumentException("Invalid batch number: $number")
        if (contents == null || contents.isEmpty()) {
            throw IllegalArgumentException("Batch #$number is empty.")
        }

        this.number = number
        this.contents = contents
    }

}