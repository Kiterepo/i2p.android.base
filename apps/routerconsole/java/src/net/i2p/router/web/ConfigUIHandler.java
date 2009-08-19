package net.i2p.router.web;

/** set the theme */
public class ConfigUIHandler extends FormHandler {
    private boolean _shouldSave;
    private String _config;
    
    @Override
    protected void processForm() {
        if (_shouldSave)
            saveChanges();
    }
    
    public void setShouldsave(String moo) { _shouldSave = true; }
    
    public void setTheme(String val) {
        _config = val;
    }
    
    private void saveChanges() {
        if (_config == null)
            return;
        if (_config.equals("default")) // obsolete
            _context.router().removeConfigSetting(CSSHelper.PROP_THEME_NAME);
        else
            _context.router().setConfigSetting(CSSHelper.PROP_THEME_NAME, _config);
        if (_context.router().saveConfig()) 
            addFormNotice("Theme change saved. <a href=\"configui.jsp\">Refresh the page</a> to actuate phase change.");
        else
            addFormNotice("Error saving the configuration (applied but not saved) - please see the error logs.");
    }
}
