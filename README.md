![Icon](assets/icon.png) Badger
===============================
[![Build][1]][2] [![Release][3]][4] [![Methods][5]][6] [![Arsenal][7]][8]

*Badger* is a generalized single purpose library for adding badges to drawables
in general and menu items in particular.

> **[sett]** (also **set**) - The earth or burrow of a badger.

Running with the Badger theme, it is all about the method `sett`. Home to the
badger, it is comprised of many tunnels and several entrances. It is the ideal
entry point to provide you with badges where mere drawables lived before.


Usage
-----

The `Badger.sett()` methods add a `BadgeDrawable` to the original drawable.
This results in a `LayerDrawable` with the `BadgeDrawable` added to the layer
with id `badger_drawable`. If the original drawable is a `LayerDrawable` having
a badge already set, this one gets reused instead.


#### Add a badge to a `MenuItem`

```java
BadgeDrawable badge = Badger.sett(menuItem, badgeFactory);
```

#### Add a badge to a `Drawable`

```java
Badger<?> badger = Badger.sett(drawable, badgeFactory);
BadgeDrawable badge = badger.badge;
drawable = badger.drawable;
```

#### The `BadgeDrawable`

The BadgeDrawable implements handling of alpha values and color filters for a
default `Drawable`. *Badger* includes a general `TextBadge` with a single
purpose implementation `CountBadge`.


#### The `BadgeDrawable.Factory`

The type of the badge itself is determined by the implementation of the
`BadgeDrawable.Factory` supplied.

```java
public interface Factory<T extends BadgeDrawable> {
    T createBadge();
}
```

The provided `BadgeDrawable` implementations define their own factories
`TextBadge.Factory` and `CountBadge.Factory`.


#### The `BadgeShape`

The `BadgeShape` is a simplified version of an Android `Shape` to be used with
`TextBadge`. With its `scale`, `aspectRatio` and `gravity` it defines the
actual size and position of the badge itself.

`BadgeShape` itself provides factories for *circle*, *square*, *oval*, *rect*,
*round-rect* and *round-square*.


![Example](assets/example.png)

```java
BadgeShape.oval(1f, 2f, Gravity.BOTTOM) // scale = 1 | aspectRatio = 2
BadgeShape.square(1f, Gravity.NO_GRAVITY, .5f) // scale = 1 | radiusFactor = 0.5
BadgeShape.circle(.5f, Gravity.END | Gravity.TOP) // scale = 0.5
```

#### scale
The `scale` determines the actual size of the badge drawable relative to the
size of the original drawable.

#### aspectRatio
The `aspectRatio` determines the actual shape of the badge itself. It is the
ratio between width and height. A value bigger than 1 makes the badge wider
than high, a value smaller than 1 makes it higher than wide.

#### gravity
The `gravity` determines the actual position of the badge inside the original
drawable. Layout directions are supported.

#### radiusFactor
The `radiusFactor` determines the actual radius of the circle, used to round the
corners of the rectangle, relative to the radius of the inner circle of the
badge.


Installation
------------

**Step 1** Add JitPack in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2** Add the dependency

```groovy
dependencies {
    compile "berlin.volders:badger:$badgerVersion"
}
```

Shortcomings
------------

The `TextBadge` only supports the RTL layout direction for Android Marshmallow
and later.

Badger requires to have the `buildToolsVersion` up-to-date. Please update to the lasted `buildToolsVersion` if the following issue appears:

    Error:(330) Attribute "colorAccent" has already been defined
    Error:(573) Attribute "titleTextColor" has already been defined
    Error:Execution failed for task ':app:processDebugResources'.
    > com.android.ide.common.process.ProcessException: Failed to execute aapt

License
-------

    Copyright (C) 2016 volders GmbH with <3 in Berlin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
   
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://travis-ci.org/volders/Badger.svg?branch=master
  [2]: https://travis-ci.org/volders/Badger
  [3]: https://jitpack.io/v/berlin.volders/badger.svg
  [4]: https://jitpack.io/#berlin.volders/badger
  [5]: https://img.shields.io/badge/Methods%20count-110-e91e63.svg
  [6]: http://www.methodscount.com/?lib=berlin.volders%3Abadger%3A%2B
  [7]: https://img.shields.io/badge/Android%20Arsenal-Badger-blue.svg?style=flat
  [8]: https://android-arsenal.com/details/1/4794
  [sett]: https://en.oxforddictionaries.com/definition/sett
