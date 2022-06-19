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

package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.Const;

/**
 * Interface for read-only shaders. It provides methods to can gain information from a shader,
 * but none to modify it in any way. Note that this interface is expected to be implemented by
 * a {@link Shader}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstShader extends Const {

}
