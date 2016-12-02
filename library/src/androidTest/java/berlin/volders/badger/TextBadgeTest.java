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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TextBadgeTest {

    final TestBadgeShape shape = new TestBadgeShape(1, 1, 0);
    final int badgeColor = 0xff654321;
    final int textColor = 0xff123456;

    TextBadge badge;

    @Before
    public void setup() {
        badge = new TextBadge(shape, badgeColor, textColor);
        badge.setText("1");
    }

    @Test
    public void draw() throws Exception {
        TestCanvas canvas = new TestCanvas();

        badge.draw(canvas);

        shape.assertCanvas(canvas);
        shape.assertBadgeColor(badgeColor);
        canvas.assertTextColor(textColor);
    }

    @Test
    public void draw_nothing_on_count_empty_string() throws Exception {
        TestCanvas canvas = new TestCanvas();
        badge.setText("");

        badge.draw(canvas);

        canvas.assertNothingDrawn();
    }

    @Test
    public void setText() throws Exception {
        String expected = badge.getText() + "::2";
        TestDrawableCallback callback = new TestDrawableCallback();
        badge.setCallback(callback);

        badge.setText(expected);

        assertThat(badge.getText(), equalTo(expected));
        callback.assertInvalidated();
    }

    @Test
    public void setText_ignore_unchanged() throws Exception {
        TestDrawableCallback callback = new TestDrawableCallback();
        badge.setCallback(callback);

        badge.setText(badge.getText());

        callback.assertNotInvalidated();
    }

    @Test
    public void getText() throws Exception {
        badge.setText("");
        assertThat(badge.getText(), equalTo(""));
        badge.setText("1");
        assertThat(badge.getText(), equalTo("1"));
        badge.setText("FooBar");
        assertThat(badge.getText(), equalTo("FooBar"));
    }

    @Test
    public void Factory() throws Exception {
        TextBadge.Factory<TextBadge> factory
                = new TextBadge.Factory<TextBadge>(shape, badgeColor, textColor) {
            @Override
            public TextBadge createBadge() {
                return null;
            }
        };

        assertThat(factory.shape, is((BadgeShape) shape));
        assertThat(factory.badgeColor, is(badgeColor));
        assertThat(factory.textColor, is(textColor));
    }
}
