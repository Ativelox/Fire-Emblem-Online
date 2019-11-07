package de.ativelox.feo.client.model.unit;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.property.EGender;
import de.ativelox.feo.client.model.property.EUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitBuilder {

    private int mX;
    private int mY;
    private EUnit mUnit;
    private EGender mGender;
    private EClass mClass;
    private String mName;
    private EAffiliation mAffiliation;
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

    public UnitBuilder() {
        mX = 0;
        mY = 0;
        mUnit = EUnit.ROY;
        mGender = EGender.MALE;
        mClass = EClass.LORD_ROY;
        mName = "Roy";
        mAffiliation = EAffiliation.ALLIED;
        mMov = 3;
        mHp = 10;
        mStr = 5;
        mSkl = 5;
        mSpd = 5;
        mLck = 5;
        mDef = 5;
        mRes = 0;
        mGrowthDef = 0;
        mGrowthHp = 0;
        mGrowthSkl = 0;
        mGrowthSpd = 0;
        mGrowthLck = 0;
        mGrowthRes = 0;

    }

    public UnitBuilder setCoordinates(int x, int y) {
        mX = x;
        mY = y;

        return this;

    }

    public UnitBuilder setUnit(EUnit unit) {
        mUnit = unit;

        return this;

    }

    public UnitBuilder setGender(EGender gender) {
        mGender = gender;

        return this;
    }

    public UnitBuilder setClass(EClass unitClass) {
        mClass = unitClass;

        return this;
    }

    public UnitBuilder setName(String name) {
        mName = name;

        return this;
    }

    public UnitBuilder setAffiliation(EAffiliation affiliation) {
        mAffiliation = affiliation;

        return this;
    }

    public UnitBuilder setStats(int hp, int str, int skl, int spd, int lck, int def, int res, int mov) {
        mHp = hp;
        mStr = str;
        mSkl = skl;
        mSpd = spd;
        mLck = lck;
        mDef = def;
        mRes = res;
        mMov = mov;

        return this;

    }

    public UnitBuilder setStatGrowth(double hp, double str, double skl, double spd, double lck, double def,
            double res) {
        mGrowthHp = hp;
        mGrowthStr = str;
        mGrowthSkl = skl;
        mGrowthSpd = spd;
        mGrowthLck = lck;
        mGrowthDef = def;
        mGrowthRes = res;

        return this;

    }

    public IUnit build() {
        return new DummyUnit(mX, mY, mUnit, mGender, mClass, mName, mAffiliation, mMov, mHp, mStr, mSkl, mSpd, mLck,
                mDef, mRes, mGrowthHp, mGrowthStr, mGrowthSkl, mGrowthSpd, mGrowthLck, mGrowthDef, mGrowthRes);

    }
}
