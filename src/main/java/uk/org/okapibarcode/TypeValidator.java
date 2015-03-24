/*
 * Copyright 2015 Robin Stuart
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.org.okapibarcode;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 *
 * @author Robin Stuart <rstuart114@gmail.com>
 */
public class TypeValidator implements IParameterValidator {
    
    /*
        This seemingly over-complex arrangement of numbers is implemented to
        support a degree of backward compatability with Zint and tBarcode
        for anyone who has already invested time in shell scripts.
    */
    
    private int[] closedPorts = {
        5, 10, 11, 12, 16, 26, 27, 28, 33, 35, 36, 38, 39, 41, 42, 43, 44, 45, 46, 48, 49,
        54, 59, 60, 61, 62, 64, 65, 69, 72, 73, 74, 75, 83, 88, 91, 93, 94, 95, 96, 97, 100, 101,
        103, 105, 107, 109, 110, 111, 131
    };
    
    @Override
    public void validate(String name, String value) throws ParameterException {
        int type = Integer.parseInt(value);
        
        if (type < 1 || type > 142) {
            throw new ParameterException("Type " + name + " not supported");
        }

        if (type >=113 && type <= 127) {
            throw new ParameterException("Type " + name + " not supported");
        }
        
        // FIXME: What happened to Code 23 support?
        if (type == 129) {
            throw new ParameterException("Code 23 not supported, yet!");
        }

        for(int i = 0; i < closedPorts.length; i++) {
            if (type == closedPorts[i]) {
                throw new ParameterException("Type " + name + " not supported");
            }
        }
    }
}
