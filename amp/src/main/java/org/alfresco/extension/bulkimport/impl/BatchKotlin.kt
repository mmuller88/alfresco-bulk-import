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

import java.util.Collections

import org.alfresco.extension.bulkimport.source.BulkImportItem
import org.alfresco.extension.bulkimport.source.BulkImportItemVersion

/**
 * This class represents a single batch of items that need to be imported.
 *
 * @author Peter Monks (pmonks@gmail.com)
 */
class BatchKotlin: Iterable<BulkImportItem<BulkImportItemVersion>>{

    val number: Int
    private val contents: List<BulkImportItem<BulkImportItemVersion>>

    constructor(number: Int,
                contents: List<BulkImportItem<BulkImportItemVersion>>){

        if (number <= 0) {
            throw IllegalArgumentException("Invalid batch number: $number")
        }

        if (contents == null || contents.isEmpty()) {
            throw IllegalArgumentException("Batch #$number is empty.")
        }

        this.number = number
        this.contents = contents
    }

    /**
     * @see java.lang.Iterable.iterator
     */
    override fun iterator(): Iterator<BulkImportItem<BulkImportItemVersion>> {
        return Collections.unmodifiableList(contents).iterator()
    }

//    /**
//     * @return The contents of this batch.
//     */
//    fun getContents(): List<BulkImportItem<BulkImportItemVersion>> {
//        return Collections.unmodifiableList(contents)
//    }


    /**
     * @return The size of (number of items in) this batch.
     */
    fun size(): Int {
        return contents!!.size
    }


    /**
     * @return The size of the batch, in bytes. Will usually be 0 for items that represent directories.
     */
    fun sizeInBytes(): Long {
        var result: Long = 0

        for (item in contents!!) {
            result += item.sizeInBytes()
        }

        return result
    }


    /**
     * @return The number of versions in this batch.
     */
    fun numberOfVersions(): Int {
        var result = 0

        for (item in contents!!) {
            val numberOfVersions = item.numberOfVersions()

            // Items with only one "version" don't get counted
            if (numberOfVersions > 1) {
                result += numberOfVersions
            }
        }

        return result
    }


    /**
     * @return The number of aspects in this batch.
     */
    fun numberOfAspects(): Int {
        var result = 0

        // Items with only one "version" don't get counted
        for (item in contents!!) {
            result += item.numberOfAspects()
        }

        return result
    }


    /**
     * @return The number of properties in this batch.
     */
    fun numberOfMetadataProperties(): Long {
        var result: Long = 0

        for (item in contents!!) {
            result += item.numberOfMetadataProperties().toLong()
        }

        return result
    }


    /**
     * @see java.lang.Object.toString
     */
    override fun toString(): String {
        val size = size()

        return "Batch #" + number + " (" + size + " item" + (if (size == 1) "" else "s") + ")"
    }
}
