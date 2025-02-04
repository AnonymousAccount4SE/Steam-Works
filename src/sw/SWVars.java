package sw;

import arc.assets.*;
import mindustry.*;
import mindustry.ctype.*;
import sw.content.*;
import sw.entities.*;

import java.util.*;

public class SWVars implements Loadable {
	public static final float maxHeatGlow = 100;

  public static void init() {
    // glenn stuff
	  Vars.mods.getScripts().runConsole("importPackage(Packages.rhino)");
    Vars.mods.getScripts().runConsole(
	    """
				function importModClass(name){

				let constr = Class.forName("rhino.NativeJavaPackage").getDeclaredConstructor(java.lang.Boolean.TYPE, java.lang.String, ClassLoader);
				constr.setAccessible(true);

				let p = constr.newInstance(true, name, Vars.mods.mainLoader());

				let scope = Reflect.get(Vars.mods.getScripts(), "scope");
				Reflect.invoke(ScriptableObject, p, "setParentScope", [scope], [Scriptable]);

				importPackage(p);\s

				}"""
    );
    Vars.mods.getScripts().runConsole("importModClass(\"sw\")");
    Vars.mods.getScripts().runConsole("importModClass(\"sw.content\")");
    Vars.mods.getScripts().runConsole("importModClass(\"sw.util\")");
    Vars.mods.getScripts().runConsole("importModClass(\"sw.world.graph\")");
	}
	/** code to erase unlocked progress on this mod */
	public static void clearUnlockModContent() {
		Vars.content.each(content -> {
			if (content instanceof UnlockableContent c && content.minfo.mod != null) {
				if (Objects.equals(c.minfo.mod.name, Vars.mods.getMod("sw").name)) c.clearUnlock();
			}
		});
	}
	/**cheating privileges*/
	public static void unlockModContent() {
		Vars.content.each(content -> {
			if (content instanceof UnlockableContent c && content.minfo.mod != null) {
				if (Objects.equals(c.minfo.mod.name, Vars.mods.getMod("sw").name)) c.unlock();
			}
		});
	}

		/**This is where you initialize your content lists. But do not forget about correct order.
		 *  correct order:
		 *  ModItems.load()
		 *  ModStatusEffects.load()
		 *  ModLiquids.load()
		 *  ModBullets.load()
		 *  ModUnitTypes.load()
		 *  ModBlocks.load()
		 *  ModPlanets.load()
		 *  ModSectorPresets.load()
		 *  ModTechTree.load()
		 * */
		public static void loadContent() {
			SWItems.load();
			SWLiquids.load();
			SWEntityMapping.load();
			SWUnitTypes.load();
			SWBlocks.load();
			SWSectorPresets.load();
			SWTechTree.load();
		}
}
