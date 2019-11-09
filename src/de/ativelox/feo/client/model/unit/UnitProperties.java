package de.ativelox.feo.client.model.unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.property.EGender;
import de.ativelox.feo.client.model.property.EUnit;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitProperties {

    private static final Path UNIT_PATH = Paths.get("res", "fe6", "units");

    private EUnit mUnit;
    private EClass mClass;
    private EGender mGender;
    private String mName;
    private int mMov;
    private int mHp;
    private int mStr;
    private int mSkl;
    private int mSpd;
    private int mLck;
    private int mDef;
    private int mRes;
    private double mGrowthHp;
    private double mGrowthStr;
    private double mGrowthSkl;
    private double mGrowthSpd;
    private double mGrowthLck;
    private double mGrowthDef;
    private double mGrowthRes;

    private String mHoverSheetName;
    private String mMoveSheetName;
    private String mMeleeAttackSheetName;
    private String mMeleeCritSheetName;
    private String mRangedAttackSheetName;
    private String mRangedCritSheetName;
    private String mDodgeSheetName;

    private String mBattlePaletteName;

    private String mAnimationHookName;

    private String mPortraitSheetName;

    private String mDeathQuote;

    private String mCommanderAttackedQuote;

    private UnitProperties() {

    }

    public static Optional<UnitProperties> load(String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Optional<UnitProperties> prop = Optional.empty();

        try (BufferedReader r = Files.newBufferedReader(UNIT_PATH.resolve(fileName))) {
            prop = Optional.of(gson.fromJson(r, UnitProperties.class));

        } catch (IOException e) {
            Logger.get().logError(e);
        }
        return prop;
    }

    public EUnit getUnit() {
        return mUnit;
    }

    public EClass getUnitClass() {
        return mClass;
    }

    public EGender getGender() {
        return mGender;
    }

    public String getName() {
        return mName;
    }

    public int getMov() {
        return mMov;
    }

    public int getHp() {
        return mHp;
    }

    public int getStr() {
        return mStr;
    }

    public int getSkl() {
        return mSkl;
    }

    public int getSpd() {
        return mSpd;
    }

    public int getLck() {
        return mLck;
    }

    public int getDef() {
        return mDef;
    }

    public int getRes() {
        return mRes;
    }

    public double getGrowthHp() {
        return mGrowthHp;
    }

    public double getGrowthStr() {
        return mGrowthStr;
    }

    public double getGrowthSkl() {
        return mGrowthSkl;
    }

    public double getGrowthSpd() {
        return mGrowthSpd;
    }

    public double getGrowthLck() {
        return mGrowthLck;
    }

    public double getGrowthDef() {
        return mGrowthDef;
    }

    public double getGrowthRes() {
        return mGrowthRes;
    }

    public String getHoverSheetName() {
        return mHoverSheetName;
    }

    public String getMeleeAttackSheetName() {
        return mMeleeAttackSheetName;
    }

    public String getRangedAttackSheetName() {
        return mRangedAttackSheetName;
    }

    public String getMeleeCritSheetName() {
        return mMeleeCritSheetName;
    }

    public String getRangedCritSheetName() {
        return mRangedCritSheetName;
    }

    public String getPortraitSheetName() {
        return mPortraitSheetName;
    }

    public String getMoveSheetName() {
        return mMoveSheetName;
    }

    public String getDodgeSheetName() {
        return mDodgeSheetName;
    }

    public String getAnimationHookName() {
        return mAnimationHookName;
    }

    public String getBattlePaletteName() {
        return mBattlePaletteName;
    }

    public String getDeathQuote() {
        return mDeathQuote;
    }

    public String getCommanderAttackedQuote() {
        return mCommanderAttackedQuote;
    }
}
