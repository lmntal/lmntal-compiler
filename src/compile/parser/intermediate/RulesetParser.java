package compile.parser.intermediate;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import runtime.Env;
import runtime.Inline;
import runtime.InlineUnit;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.InterpretedRuleset;
import runtime.Rule;
import runtime.Ruleset;

import compile.Module;
import compile.parser.ParseException;
import compile.structure.Membrane;

public class RulesetParser {
	/** 
	 * 中間命令列を読み込み、読み込んだ最初のルールセット（初期データ生成用ルールセット）を返す。
	 * @param reader 中間命令列を読み込む Reader
	 * @throws ParseException 読み込みに失敗した場合。I/O エラーを含む
	 */
	public static Ruleset parse(Reader reader) throws ParseException {
		Lexer lexer = new Lexer(reader);
		parser parser = new parser(lexer);
		ArrayList rulesets, modules, inlines;	
		
		try {
			Object[] t = (Object[])parser.parse().value;
			rulesets = (ArrayList)t[0];
			modules = (ArrayList)t[1];
			inlines = (ArrayList)t[2];
		} catch (IOException e) {
			Env.error("ERROR: failed to read input data.");
			throw new ParseException();
		} catch (Exception e) {
			Env.error("ERROR: " + e.getMessage());
			throw new ParseException();
		}

		// id -> 実体のマップ生成
		HashMap rulesetMap = new HashMap();
		Iterator rsIt = rulesets.iterator();
		while (rsIt.hasNext()) {
			InterpretedRuleset rs = (InterpretedRuleset)rsIt.next();
			rulesetMap.put(new Integer(rs.getId()), rs);
		}
		
		// RulesetRef を、実際のルールセットに置き換える
		rsIt = rulesets.iterator();
		while (rsIt.hasNext()) {
			InterpretedRuleset rs = (InterpretedRuleset)rsIt.next();
			Iterator ruleIt = rs.rules.iterator();
			while (ruleIt.hasNext()) {
				Rule rule = (Rule)ruleIt.next();
				updateRef(rule.atomMatch, rulesetMap, rule.guardLabel, rule.bodyLabel);
				updateRef(rule.memMatch, rulesetMap, rule.guardLabel, rule.bodyLabel);
				updateRef(rule.guard, rulesetMap, rule.guardLabel, rule.bodyLabel);
				updateRef(rule.body, rulesetMap, rule.guardLabel, rule.bodyLabel);
			}
		}
		
		// モジュールの処理
		Iterator it = modules.iterator();
		while (it.hasNext()) {
			Membrane mem = (Membrane)it.next();
			ListIterator lit = mem.rulesets.listIterator();
			while (lit.hasNext()) {
				Integer id = (Integer)lit.next();
				lit.set(rulesetMap.get(id));
			}
			Module.regMemName(mem.name, mem);
		}
		
		// インラインの処理
		it = inlines.iterator();
		while (it.hasNext()) {
			String name = (String)it.next();
			InlineUnit iu = new InlineUnit(name);
			iu.attach();
			Inline.inlineSet.put(name, iu);
		}

		return (Ruleset)rulesets.get(0);
	}
	
	private static void updateRef(List insts, Map map, InstructionList guard, InstructionList body) {
		if (insts == null) return;
		Integer guardLabel, bodyLabel;
		guardLabel = (guard == null) ? null : Integer.valueOf(guard.label.substring(1));
		bodyLabel = (body == null) ? null : Integer.valueOf(body.label.substring(1));

		Iterator it = insts.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			for (int i = 0; i < inst.data.size(); i++) {
				Object data = inst.data.get(i);
				if (data instanceof RulesetRef) {
					inst.data.set(i, map.get(((RulesetRef)data).getId()));
				} else if (data instanceof LabelRef) {
					Integer id = ((LabelRef)data).getId();
					if (id.equals(guardLabel)) {
						inst.data.set(i, guard);
					} else if (id.equals(bodyLabel)) {
						inst.data.set(i, body);
					} else {
						Env.error("ERROR : invalid label L" + id);
					}
				} else if (data instanceof InstructionList) {
					updateRef(((InstructionList)data).insts, map, guard, body);
				}
			}
		}
	}
}
