package de.luc1412.em.utils;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Luc1412 on 06.10.2017 at 22:17
 */
public class EmTitleAnimation {

	private Player p;

	public EmTitleAnimation(Player p) {
		this.p = p;
		startAnimation();
	}

	private void startAnimation() {
		String rawTitle = "EasyMaintenance";
		String rawSubtitle = "The modern and easy maintenance Plugin by Luc1412"; //51
		char[] subtitleAsChars = rawSubtitle.toCharArray();
		char[] titleAsChars = rawTitle.toCharArray();
		final String[] subTitle = {""};
		final String[] title = {""};
		new NoteblockSongPlayer(p);

		Thread animation1 = new Thread(() -> {
			try {
				for (int i = 0; i < (subtitleAsChars.length + titleAsChars.length); i++) {
					if (i <= titleAsChars.length - 1) {
						EasyMaintenance.getUtils().sendTitle(p, title[0], "", 0, 10, 30);
						title[0] += titleAsChars[i];
					} else {
						subTitle[0] += subtitleAsChars[i - titleAsChars.length];
						if (i == 15)
							EasyMaintenance.getUtils().sendTitle(p, "§cE§rasy§aM§raintenance", subTitle[0], 0, 10, 30);
						if (i == 16)
							EasyMaintenance.getUtils().sendTitle(p, "§cEa§rsy§aMa§rintenance", subTitle[0], 0, 10, 30);
						if (i == 17)
							EasyMaintenance.getUtils().sendTitle(p, "§4E§casy§aMai§rntenance", subTitle[0], 0, 10, 30);
						if (i == 18)
							EasyMaintenance.getUtils().sendTitle(p, "§4Ea§csy§2M§aain§rtenance", subTitle[0], 0, 10, 30);
						if (i == 19)
							EasyMaintenance.getUtils().sendTitle(p, "§4Eas§cy§2Ma§aint§renance", subTitle[0], 0, 10, 30);
						if (i == 20)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Mai§ante§rnance", subTitle[0], 0, 10, 30);
						if (i == 21)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Main§aten§rance", subTitle[0], 0, 10, 30);
						if (i == 22)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Maint§aena§rnce", subTitle[0], 0, 10, 30);
						if (i == 23)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Mainte§anan§rce", subTitle[0], 0, 10, 30);
						if (i == 24)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Mainten§aanc§re", subTitle[0], 0, 10, 30);
						if (i == 25)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Maintena§ance", subTitle[0], 0, 10, 30);
						if (i == 26)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Maintenan§ace", subTitle[0], 0, 10, 30);
						if (i == 27)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Maintenanc§ae", subTitle[0], 0, 10, 30);
						if (i > 28)
							EasyMaintenance.getUtils().sendTitle(p, "§4Easy§2Maintenance", subTitle[0], 0, 10, 170);
						if (EasyMaintenance.getUtils().getCurrentVersionAsInt() == 8) {
							p.playSound(p.getLocation(), Sound.valueOf("DIG_STONE"), 1000, 2F);
						} else p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ANVIL_BREAK"), 1000, 2F);

					}

					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread animation2 = new Thread(() -> {
			try {
				for (int i = 0; i < (subtitleAsChars.length + titleAsChars.length); i++) {
					if (i <= 14) {
						EasyMaintenance.getUtils().sendTitle(p, title[0], "", 0, 10, 30);
						title[0] += titleAsChars[i];
					} else {
						subTitle[0] += subtitleAsChars[i - 15];
						if (i == 15)
							EasyMaintenance.getUtils().sendTitle(p, "§9E§rasyMaintenance", subTitle[0], 0, 10, 30);
						if (i == 16)
							EasyMaintenance.getUtils().sendTitle(p, "§9Ea§rsyMaintenance", subTitle[0], 0, 10, 30);
						if (i == 17)
							EasyMaintenance.getUtils().sendTitle(p, "§9Eas§ryMaintenance", subTitle[0], 0, 10, 30);
						if (i == 18)
							EasyMaintenance.getUtils().sendTitle(p, "§9Easy§rMaintenance", subTitle[0], 0, 10, 30);
						if (i == 19)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§cM§raintenance", subTitle[0], 0, 10, 30);
						if (i == 20)
							EasyMaintenance.getUtils().sendTitle(p, "§1E§9asy§cMa§rintenance", subTitle[0], 0, 10, 30);
						if (i == 21)
							EasyMaintenance.getUtils().sendTitle(p, "§1Ea§9sy§cMai§rntenance", subTitle[0], 0, 10, 30);
						if (i == 22)
							EasyMaintenance.getUtils().sendTitle(p, "§1Eas§9y§cMain§rtenance", subTitle[0], 0, 10, 30);
						if (i == 23)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2M§caint§renance", subTitle[0], 0, 10, 30);
						if (i == 24)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Ma§cinte§rnance", subTitle[0], 0, 10, 30);
						if (i == 25)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Mai§cnten§rance", subTitle[0], 0, 10, 30);
						if (i == 26)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Main§ctena§rnce", subTitle[0], 0, 10, 30);
						if (i == 27)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Maint§cenan§rce", subTitle[0], 0, 10, 30);
						if (i == 28)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Mainte§cnanc§re", subTitle[0], 0, 10, 30);
						if (i == 29)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Mainten§cance", subTitle[0], 0, 10, 30);
						if (i == 30)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Maintena§cnce", subTitle[0], 0, 10, 30);
						if (i == 31)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Maintenan§cce", subTitle[0], 0, 10, 30);
						if (i == 32)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Maintenanc§ce", subTitle[0], 0, 10, 30);
						if (i > 33)
							EasyMaintenance.getUtils().sendTitle(p, "§1Easy§2Maintenance", subTitle[0], 0, 10, 30);
						if (EasyMaintenance.getUtils().getCurrentVersionAsInt() == 8) {
							p.playSound(p.getLocation(), Sound.valueOf("ANVIL_BREAK"), 1000, 2F);
						} else p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ANVIL_BREAK"), 1000, 2F);

					}
				}
				Thread.sleep(40);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		animation1.start();


	}

}
