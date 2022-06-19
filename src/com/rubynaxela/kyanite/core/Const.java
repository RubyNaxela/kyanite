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

/**
 * Interface for read-only objects. This interface is Kyanite's way to imitate the C++ {@code const} keyword and to map
 * C++ const references to Java. Subinterfaces will be specific to a certain Kyanite type and provide methods to read
 * from them only. The subinterfaces may be used freely outside of the Kyanite API itself, however, it is not recommended
 * to implement them, because in certain cases, they are expected to be implemented by a certain Kyanite class.
 */
public interface Const {

}
