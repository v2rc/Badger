/*
 * Copyright (C) 2016 volders GmbH with <3 in Berlin
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

package berlin.volders.badger;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

class TestBadgeDrawable extends BadgeDrawable {

    private boolean invalidated;

    @Override
    public void draw(@NonNull Canvas canvas) {
    }

    @Override
    public void invalidateSelf() {
        super.invalidateSelf();
        invalidated = true;
    }

    void assertInvalidated() {
        assertThat(invalidated, is(true));
        invalidated = false;
    }

    void assertNotInvalidated() {
        assertThat(invalidated, is(false));
    }
}
