package translated;
import translated.module_codec.*;
import runtime.SystemRulesets;
import runtime.Ruleset;
public class Module_codec{
	private static Ruleset[] rulesets = {Ruleset603.getInstance()};
	public static Ruleset[] getRulesets() {
		return rulesets;
	}
	public static void loadUserDefinedSystemRuleset() {
		loadSystemRulesetFromModule("nlmem");
		runtime.Inline.inlineSet.put("module_nlmem.lmn", new runtime.InlineUnit("nlmem.lmn"));
		runtime.Inline.inlineSet.put("public/codec.lmn", new runtime.InlineUnit("public/codec.lmn"));
	}
	private static void loadSystemRulesetFromModule(String moduleName) {
		try {
			Class c = Class.forName("translated.Module_" + moduleName);
			java.lang.reflect.Method method = c.getMethod("loadUserDefinedSystemRuleset", null);
			method.invoke(null, null);
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		} catch (java.lang.reflect.InvocationTargetException e) {
		}
	}
}
