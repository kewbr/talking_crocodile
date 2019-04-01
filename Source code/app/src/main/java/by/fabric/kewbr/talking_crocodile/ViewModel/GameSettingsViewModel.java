package by.fabric.kewbr.talking_crocodile.ViewModel;

import by.fabric.kewbr.talking_crocodile.Model.SettingsModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class GameSettingsViewModel {

    private Realm databaseInstance;
    public SettingsModel settings;

    public GameSettingsViewModel() {

        this.databaseInstance = Realm.getDefaultInstance();

        this.settings = databaseInstance
                .where(SettingsModel.class).findFirst();

        if (this.settings == null) {

            this.settings = new SettingsModel();
            this.settings.setTopic("easy");
            this.settings.setInAppSound(true);
            this.settings.setWordsForWinCount(60);
            this.settings.setDurationOfRound(Long.valueOf(60));
            this.settings.setChargeForPassing(false);

        }
    }

    public void manageSettings(SettingsModel newSettings) {
        if (newSettings.getDurationOfRound() != this.settings.getDurationOfRound() ||
        newSettings.getTopic() != this.settings.getTopic() ||
        newSettings.isChargeForPassing() != this.settings.isChargeForPassing() ||
        newSettings.isInAppSound() != this.settings.isInAppSound() ||
        newSettings.getWordsForWinCount() != this.settings.getWordsForWinCount()) {
            this.removeOldSavedSettings();
            this.saveNewSettings(newSettings);
        }
    }

    public void removeOldSavedSettings() {
        databaseInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SettingsModel> result = realm.where(SettingsModel.class).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public void saveNewSettings(SettingsModel newSettings) {
        databaseInstance.beginTransaction();

        SettingsModel settingsToStore = databaseInstance.copyToRealm(newSettings);
        this.settings = settingsToStore;

        databaseInstance.commitTransaction();
    }

}
