package com.reimu747.pokemon.model.enums;

/**
 * @Author Reimu747
 */
public enum NatureEnum
{
    // 25种性格
    HARDY(1, null, null),
    LONELY(2, AbilityEnum.ATTACK, AbilityEnum.DEFENSE),
    BRAVE(3, AbilityEnum.ATTACK, AbilityEnum.SPEED),
    ADAMANT(4, AbilityEnum.ATTACK, AbilityEnum.SPECIAL_ATTACK),
    NAUGHTY(5, AbilityEnum.ATTACK, AbilityEnum.SPECIAL_DEFENSE),

    BOLD(6, AbilityEnum.DEFENSE, AbilityEnum.ATTACK),
    DOCILE(7, null, null),
    RELAXED(8, AbilityEnum.DEFENSE, AbilityEnum.SPEED),
    IMPISH(9, AbilityEnum.DEFENSE, AbilityEnum.SPECIAL_ATTACK),
    LAX(10, AbilityEnum.DEFENSE, AbilityEnum.SPECIAL_DEFENSE),

    TIMID(11, AbilityEnum.SPEED, AbilityEnum.ATTACK),
    HASTY(12, AbilityEnum.SPEED, AbilityEnum.DEFENSE),
    SERIOUS(13, null, null),
    JOLLY(14, AbilityEnum.SPEED, AbilityEnum.SPECIAL_ATTACK),
    NAIVE(15, AbilityEnum.SPEED, AbilityEnum.SPECIAL_DEFENSE),

    MODEST(16, AbilityEnum.SPECIAL_ATTACK, AbilityEnum.ATTACK),
    MILD(17, AbilityEnum.SPECIAL_ATTACK, AbilityEnum.DEFENSE),
    QUIET(18, AbilityEnum.SPECIAL_ATTACK, AbilityEnum.SPEED),
    BASHFUL(19, null, null),
    RASH(20, AbilityEnum.SPECIAL_ATTACK, AbilityEnum.SPECIAL_DEFENSE),

    CALM(21, AbilityEnum.SPECIAL_DEFENSE, AbilityEnum.ATTACK),
    GENTLE(22, AbilityEnum.SPECIAL_DEFENSE, AbilityEnum.DEFENSE),
    SASSY(23, AbilityEnum.SPECIAL_DEFENSE, AbilityEnum.SPEED),
    CAREFUL(24, AbilityEnum.SPECIAL_DEFENSE, AbilityEnum.SPECIAL_ATTACK),
    QUIRKY(25, null, null);

    private int id;
    private AbilityEnum incraseAbility;
    private AbilityEnum decraseAbility;

    NatureEnum(int id, AbilityEnum incraseAbility, AbilityEnum decraseAbility)
    {
        this.id = id;
        this.incraseAbility = incraseAbility;
        this.decraseAbility = decraseAbility;
    }

    public int getId()
    {
        return id;
    }

    public AbilityEnum getIncraseAbility()
    {
        return incraseAbility;
    }

    public AbilityEnum getDecraseAbility()
    {
        return decraseAbility;
    }
}
