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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("WeakerAccess")
public class CountBadgeTest {

    final int badgeColor = 0xff654321;
    final int textColor = 0xff123456;

    CountBadge badge;
    TestBadgeShape shape;

    @Before
    public void setup() {
        shape = new TestBadgeShape(1, 1, 0);
        badge = new CountBadge(shape, badgeColor, textColor);
        badge.setCount(1);
    }

    @Test
    public void draw() {
        TestCanvas canvas = new TestCanvas();

        badge.draw(canvas);

        shape.assertCanvas(canvas);
        shape.assertBadgeColor(badgeColor);
        canvas.assertTextColor(textColor);
    }

    @Test
    public void draw_nothing_on_count_0() {
        TestCanvas canvas = new TestCanvas();
        badge.setCount(0);

        badge.draw(canvas);

        canvas.assertNothingDrawn();
    }

    @Test
    public void setCount() {
        int expected = badge.getCount() + 43;
        TestDrawableCallback callback = new TestDrawableCallback();
        badge.setCallback(callback);

        badge.setCount(expected);

        assertThat(badge.getCount(), is(expected));
        callback.assertInvalidated();
    }

    @Test
    public void setCount_ignore_unchanged() {
        TestDrawableCallback callback = new TestDrawableCallback();
        badge.setCallback(callback);

        badge.setCount(badge.getCount());

        callback.assertNotInvalidated();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCount_fail_on_negative() {
        //noinspection Range
        badge.setCount(-1);
    }

    @Test
    public void getCount() {
        badge.setCount(0);
        assertThat(badge.getCount(), is(0));
        badge.setCount(1);
        assertThat(badge.getCount(), is(1));
        badge.setCount(2);
        assertThat(badge.getCount(), is(2));
    }

    @Test
    public void Factory() {
        TestCanvas canvas = new TestCanvas();
        CountBadge.Factory factory = new CountBadge.Factory(shape, badgeColor, textColor);

        CountBadge badge = factory.createBadge();
        badge.setCount(1);
        badge.draw(canvas);

        shape.assertCanvas(canvas);
        shape.assertBadgeColor(badgeColor);
        canvas.assertTextColor(textColor);
        assertThat(factory.shape, is((BadgeShape) shape));
        assertThat(factory.badgeColor, is(badgeColor));
        assertThat(factory.textColor, is(textColor));
    }
}
