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

import android.graphics.ColorFilter;
import android.graphics.PixelFormat;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BadgeDrawableTest {

    TestBadgeDrawable badgeDrawable;

    @Before
    public void setup() {
        badgeDrawable = new TestBadgeDrawable();
    }

    @Test
    @SuppressWarnings("Range")
    public void setAlpha() throws Exception {
        badgeDrawable.setAlpha(0);
        assertThat(badgeDrawable.getAlpha(), is(0));
        badgeDrawable.assertInvalidated();

        badgeDrawable.setAlpha(1);
        assertThat(badgeDrawable.getAlpha(), is(1));
        badgeDrawable.assertInvalidated();

        badgeDrawable.setAlpha(255);
        assertThat(badgeDrawable.getAlpha(), is(255));
        badgeDrawable.assertInvalidated();

        badgeDrawable.setAlpha(-1);
        assertThat(badgeDrawable.getAlpha(), is(0));
        badgeDrawable.assertInvalidated();

        badgeDrawable.setAlpha(256);
        assertThat(badgeDrawable.getAlpha(), is(255));
        badgeDrawable.assertInvalidated();

        badgeDrawable.setAlpha(512);
        assertThat(badgeDrawable.getAlpha(), is(255));
        badgeDrawable.assertNotInvalidated();
    }

    @Test
    public void getAlpha() throws Exception {
        assertThat(badgeDrawable.getAlpha(), is(255));
        badgeDrawable.setAlpha(43);
        assertThat(badgeDrawable.getAlpha(), is(43));
    }

    @Test
    public void setColorFilter() throws Exception {
        ColorFilter colorFilter = new ColorFilter();

        badgeDrawable.setColorFilter(colorFilter);
        assertThat(badgeDrawable.getColorFilter(), is(colorFilter));
        badgeDrawable.assertInvalidated();

        badgeDrawable.setColorFilter(colorFilter);
        assertThat(badgeDrawable.getColorFilter(), is(colorFilter));
        badgeDrawable.assertNotInvalidated();

        badgeDrawable.setColorFilter(new ColorFilter());
        assertThat(badgeDrawable.getColorFilter(), not(colorFilter));
        badgeDrawable.assertInvalidated();

        badgeDrawable.setColorFilter(null);
        assertThat(badgeDrawable.getColorFilter(), nullValue());
        badgeDrawable.assertInvalidated();
    }

    @Test
    public void getColorFilter() throws Exception {
        assertThat(badgeDrawable.getColorFilter(), nullValue());
        badgeDrawable.setColorFilter(new ColorFilter());
        assertThat(badgeDrawable.getColorFilter(), notNullValue());
    }

    @Test
    public void getOpacity() throws Exception {
        badgeDrawable.setAlpha(0);
        assertThat(badgeDrawable.getOpacity(), is(PixelFormat.TRANSPARENT));

        badgeDrawable.setAlpha(1);
        assertThat(badgeDrawable.getOpacity(), is(PixelFormat.TRANSLUCENT));

        badgeDrawable.setAlpha(255);
        assertThat(badgeDrawable.getOpacity(), is(PixelFormat.OPAQUE));
    }
}
