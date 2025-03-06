package com.pavelsikun.seekbarpreference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.NonNull;
//import android.support.v7.preference.Preference;
import androidx.preference.Preference;
//import android.support.v7.preference.PreferenceViewHolder;
import androidx.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import net.i2p.android.router.R;

public class SeekBarPreference extends Preference implements Persistable {

    private MaterialSeekBarController mController;

    public SeekBarPreference(Context context) {
        super(context);
        init(null);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setLayoutResource(R.layout.seekbar_preference);
        mController = new MaterialSeekBarController(getContext(), attrs, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder viewHolder) {
        super.onBindViewHolder(viewHolder);
        mController.onBindView(viewHolder.itemView);
    }

    @Override
    protected Object onGetDefaultValue(@NonNull TypedArray ta, int index) {
        if(mController != null) return ta.getInt(index, mController.getCurrentValue());
        else return null;
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, @NonNull Object defaultValue) {
        int average = mController.getMaxValue() / 2;
        if(restoreValue) mController.setCurrentValue(getPersistedInt(average));
        else mController.onSetInitialValue(restoreValue, defaultValue);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mController.setEnabled(enabled);
    }

    @Override
    public void onDependencyChanged(Preference dependency, boolean disableDependent) {
        super.onDependencyChanged(dependency, disableDependent);
        mController.onDependencyChanged(dependency, disableDependent);
    }

    @Override
    public void onPersist(int value) {
        persistInt(value);
        notifyChanged();
    }

    public String getMeasurementUnit() {
        return mController.getMeasurementUnit();
    }

    public void setMeasurementUnit(String measurementUnit) {
        mController.setMeasurementUnit(measurementUnit);
    }

    public int getMaxValue() {
        return mController.getMaxValue();
    }

    public void setMaxValue(int maxValue) {
        mController.setMaxValue(maxValue);
    }

    public int getCurrentValue() {
        return mController.getCurrentValue();
    }

    public void setCurrentValue(int value) {
        mController.setCurrentValue(value);
    }
}
