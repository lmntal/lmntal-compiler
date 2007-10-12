/*
 * task.h
 */

#ifndef LMN_TASK_H
#define LMN_TASK_H

#include "membrane.h"

/* 中間命令で出現するデータ構造
 * LINK_LIST    リンクオブジェクトのリスト
 * LIST_AND_MAP 第１要素がリンクオブジェクトのリストで第２要素がマップ
 * MAP          マップ
 */
#define LINK_LIST     1
#define LIST_AND_MAP  2
#define MAP           3

void memstack_push(LmnMembrane *mem);

#endif
