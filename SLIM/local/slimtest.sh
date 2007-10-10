#!/bin/bash

# slimtest.sh
# 2007/09/13 sasaki
# 2007/10/10 riki

# ---- 概要 ----
# SLIMの吐き出した結果が正しいかどうかを判定するスクリプトです.
#
# 広戸くんのハッシュコード算出器を用いて,
# Java処理系上で実行(lmntal --hiderule --hidruleset)して得られるグラフ構造のハッシュ値h_aと,
# SLIM上で実行(slim --hiderule)して得られるグラフ構造のハッシュ値h_b
# を計算し, h_a = h_b であったならば
# "success", そうでない場合は "failure" を返します.
#
# ---- 使用例 ----
#   (hoge.lmn) {a, a:-b}.
#   (javaの吐き出した結果 (java.out)) {b}
#   (SLIMの吐き出した結果 (slim.out)) {b}
#   のもとで
#   $ ./slimtest.sh hoge.lmn slim.out
#   success
#
# (注) lmntal実行ファイルへのパスを通しておいてください

hash_lmn="__temp_hash.lmn"
java_lmn="__temp_java.lmn"
slim_lmn="__temp_slim.lmn"
res_java_lmn="__temp_res_java.lmn"
res_slim_lmn="__temp_res_slim.lmn"


if [ ! -f "$1" ] || [ ! -f "$2" ]; then
	echo Usage:
	printf "\t$0 [result by lmntal --hiderule --hideruleset] [result by slim --hiderule]\n"
	exit 1
fi

# ----------------------- #
#  build hash calculator  #
# ----------------------- #
echo "/* (c) Kohei HIROTO 07/01/16 */{module(mhash). mhash.use:-. [:/*inline_define*/ import java.util.*; import util.*; class MembraneHashCodeCalculator {static int calculate(Membrane m) {return calculate(m, new HashMap<Membrane, Integer>());}private static int calculate(Membrane m, Map<Membrane, Integer> m2hc) {final long MAX_VALUE = Integer.MAX_VALUE;long add = 3412;long mult = 3412;Atom a = null;Membrane mm = null;QueuedEntity q = null;Set<QueuedEntity> contents = new HashSet<QueuedEntity>(), toCalculate = new HashSet<QueuedEntity>(), calculated = new HashSet<QueuedEntity>();for (Iterator i = m.atomIterator(); i.hasNext(); ) {a = (Atom) i.next();if (a.getFunctor().isOutsideProxy() || a.getFunctor().isInsideProxy()) {continue;}contents.add(a);}for (Iterator i = m.memIterator(); i.hasNext(); ) {mm = (Membrane) i.next();contents.add(mm);m2hc.put(mm, calculate(mm, m2hc));}while (!contents.isEmpty()) {q = contents.iterator().next();contents.remove(q);long mol = -1, mol_add = 0, mol_mult = 41, temp = 0;toCalculate.clear();calculated.clear();toCalculate.add(q);while (!toCalculate.isEmpty()) {q = toCalculate.iterator().next();calculated.add(q);toCalculate.remove(q);if (q instanceof Atom) {a = (Atom) q;temp = a.getFunctor().hashCode();int arity = a.getFunctor().getArity();for (int k = 0; k < arity; k++) {temp *= 31;Link link = a.getArg(k);if (link.getAtom().getFunctor().isInsideProxy()) {Atom inside = link.getAtom();int pos = link.getPos() + 1;temp += (inside.getFunctor().hashCode() * pos);} else if (link.getAtom().getFunctor().isOutsideProxy()) {int t = 0;mm = link.getAtom().nthAtom(0).getMem();if (!calculated.contains(mm)) {toCalculate.add(mm);}while (link.getAtom().getFunctor().isOutsideProxy()) {link = link.getAtom().nthAtom(0).getArg(1);mm = link.getAtom().getMem();t += m2hc.get(mm);t *= 13;}t *= link.getAtom().getFunctor().hashCode();t *= link.getPos() + 1;temp += t;} else {Atom linked = link.getAtom();if (!calculated.contains(linked)) {toCalculate.add(linked);}int pos = link.getPos() + 1;temp += (linked.getFunctor().hashCode() * pos);}}} else {Membrane mt = (Membrane) q;final int thisMembsHC = m2hc.get(mt);temp = thisMembsHC;Link link = null;for (Iterator i = mt.atomIteratorOfFunctor(Functor.INSIDE_PROXY); i.hasNext(); ) {Atom inside = (Atom) i.next();int s = 0;link = inside.nthAtom(0).getArg(1);if (link.getAtom().getFunctor().isOutsideProxy()) {mm = link.getAtom().nthAtom(0).getMem();if (!calculated.contains(mm)) {toCalculate.add(mm);}} else {a = link.getAtom();if (!calculated.contains(a)) {toCalculate.add(a);}}while (link.getAtom().getFunctor().isOutsideProxy()) {link = link.getAtom().nthAtom(0).getArg(1);s += m2hc.get(link.getAtom().getMem());s *= 13;}s += link.getAtom().getFunctor().hashCode();s *= link.getPos() + 1;int t = 0;link = inside.getArg(1);while (link.getAtom().getFunctor().isOutsideProxy()) {link = link.getAtom().nthAtom(0).getArg(1);t += m2hc.get(link.getAtom().getMem());t *= 13;}t *= link.getAtom().getFunctor().hashCode();t *= link.getPos() + 1;temp += thisMembsHC^t * s;}}mol_add += temp;mol_add %= MAX_VALUE;mol_mult *= temp;mol_mult %= MAX_VALUE;}mol = mol_add^mol_mult;contents.removeAll(calculated);add += mol;add %= MAX_VALUE;mult *= mol;mult %= MAX_VALUE;}return (int) (mult^add);}}:].H = mhash.hashCode(Memb, MR) :- H=[:/*inline*/Atom a = me.nthAtom(0).nthAtom(0);Membrane m = (Membrane) (a.getMem());int hashCode = MembraneHashCodeCalculator.calculate(m);Atom result = mem.newAtom(new IntegerFunctor(hashCode));mem.relinkAtomArgs(result, 0, me, 2);mem.unifyAtomArgs(me, 0, me, 1);me.remove();:](Memb, MR).}." >"$hash_lmn"


# -------------- #
#  Main Routine  #
# -------------- #
echo "SLIMTEST__M={`cat $1`}, slimtest__hc=mhash.hashCode(SLIMTEST__M,m)." >"$java_lmn"
echo "SLIMTEST__M={`cat $2`}, slimtest__hc=mhash.hashCode(SLIMTEST__M,m)." >"$slim_lmn"

grep_pattern="slimtest__hc[[:blank:]]*(\([-[:digit:]]*\))"

java_hashval=`lmntal "$hash_lmn" "$java_lmn" | grep -oe "$grep_pattern" | sed -ne "s/$grep_pattern/\1/p"`
slim_hashval=`lmntal "$hash_lmn" "$slim_lmn" | grep -oe "$grep_pattern" | sed -ne "s/$grep_pattern/\1/p"`

if [ "$java_hashval" -eq "$slim_hashval" ];
then
    echo "success"
else
    echo "failure"
fi

rm -f "$java_lmn"
rm -f "$slim_lmn"
rm -f "$hash_lmn"

exit 0
