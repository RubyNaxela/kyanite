package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.RenderStates;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.graphics.RenderTarget;

@Deprecated
@Intercom
public interface Drawable {

    @Intercom
    void draw(@NotNull RenderTarget target, @NotNull RenderStates states);
}
