/**
 *  Copyright 2007 Rutgers, the State University of New Jersey
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inspektr.audit.spi;

import org.aspectj.lang.JoinPoint;
import org.inspektr.audit.annotation.Auditable;
import org.inspektr.common.spi.ActionResolver;

/**
 * An SPI interface needed to be implemented by individual applications requiring an audit trail record keeping
 * functionality, to provide the action taken.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface AuditableActionResolver extends ActionResolver<Auditable> {
	

    /**
     * Resolve the action for the auditable event.
     * 
     * @param auditableTarget
     * @param retval	The returned value
     * @param auditable the Auditable annotation that may contain additional information.
     * @return	The resource String
     */
    String resolveFrom(JoinPoint auditableTarget, Object retval, Auditable auditable);
    
    /**
     * Resolve the action for the auditable event that has incurred
     * an exception.
     * 
     * @param auditableTarget
     * @param exception	The exception incurred when the join point proceeds.
     * @param auditable the Auditable annotation that may contain additional information.
     * @return	The resource String
     */
    String resolveFrom(JoinPoint auditableTarget, Exception exception, Auditable auditable);

}
