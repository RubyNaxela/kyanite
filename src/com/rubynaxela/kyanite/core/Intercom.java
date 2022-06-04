/*
 * Copyright (c) 2021-2022 Alex Pawelski
 *
 * Licensed under the Silicon License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://rubynaxela.github.io/Silicon-License/plain_text.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.rubynaxela.kyanite.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotation for intercom-critical types, fields, methods and constructors. This annotation is
 * purely for informational purposes and provides no actual functionality. Elements annotated by
 * this annotation must not be refactored without altering the respective native (C++) sources.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Intercom {

}
