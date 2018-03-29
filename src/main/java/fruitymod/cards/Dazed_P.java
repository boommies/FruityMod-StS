package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Dazed_P extends CustomCard {
	public static final String ID = "Dazed P";
	public static final String NAME = "Dazed P";
	//private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = "Ethereal. Gain !B! Block. Deal !D! damage to ALL enemies."; //cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int ATTACK_DMG = 0;
	//private static final int UPGRADE_DMG_AMT = 0;
	private static final int BLOCK_AMT = 0;
	//private static final int UPGRADE_BLOCK_AMT = 0;
	private static final int POOL = 0;

	public Dazed_P() {
		super(ID, NAME, "img/cards/status/dazed", COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
		// public CustomCard(String id, String name, String img, int cost, String rawDescription, 
				//CardType type, CardColor color, 
				//CardRarity rarity, CardTarget target, int cardPool) {
		
		this.baseDamage = ATTACK_DMG;
		this.baseBlock = BLOCK_AMT;
		this.isEthereal = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
		if (p != null && m != null) {
			AbstractDungeon.actionManager
					.addToBottom(new VFXAction(new IronWaveEffect(p.hb.cX, p.hb.cY, m.hb.cX), 0.5f));
		}
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction((AbstractCreature) m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_VERTICAL));
	}

	@Override
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Dazed_P();
	}

	@Override
	public void upgrade() {
	}
}
