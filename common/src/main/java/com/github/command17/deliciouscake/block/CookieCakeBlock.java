package com.github.command17.deliciouscake.block;

public class CookieCakeBlock extends CustomCakeBlock {
    public CookieCakeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getFoodLevelModifier() {
        return 1;
    }
}
