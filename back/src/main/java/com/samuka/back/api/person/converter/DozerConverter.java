/*
 * Copyright 2021 Samuka <samuelgenio28@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.samuka.back.api.person.converter;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuka <samuelgenio28@gmail.com>
 */
public class DozerConverter {
    
    private static Mapper maper = DozerBeanMapperBuilder.buildDefault();
 
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return maper.map(origin, destination);
    }
    
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        
        for (Object o : origin) {
            destinationObjects.add(maper.map(o, destination));
        }
        
        return destinationObjects;
    }
    
}
