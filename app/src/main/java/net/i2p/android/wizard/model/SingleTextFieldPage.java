/*
 * Copyright 2013 str4d
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.i2p.android.wizard.model;

import net.i2p.android.wizard.ui.SingleTextFieldFragment;

//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * A page asking for a text field.
 */
public class SingleTextFieldPage extends Page {
    // The null is checked in SingleTextFieldFragment
    protected String mDef = null;
    protected String mDesc = "";
    protected boolean mNumeric = false;
    private String mFeedback;

    public SingleTextFieldPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return SingleTextFieldFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return (!TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY))) && isValid();
    }

    public SingleTextFieldPage setDefault(String def) {
        mDef = def;
        return this;
    }

    public String getDefault() {
        return mDef;
    }

    public SingleTextFieldPage setDescription(String desc) {
        mDesc = desc;
        return this;
    }

    public String getDesc() {
        return mDesc;
    }

    public SingleTextFieldPage setNumeric(boolean numeric) {
        mNumeric = numeric;
        return this;
    }

    public boolean getNumeric() {
        return mNumeric;
    }

    // Override these in subclasses to add content verification.

    public boolean isValid() {
        if (mNumeric) {
            try {
                //noinspection ResultOfMethodCallIgnored
                Integer.parseInt(mData.getString(SIMPLE_DATA_KEY));
            } catch (NumberFormatException e) {
                mFeedback = "Not a number";
                return false;
            }
        }
        mFeedback = "";
        return true;
    }

    public boolean showFeedback() {
        return true;
    }

    public String getFeedback() {
        return mFeedback;
    }
}
