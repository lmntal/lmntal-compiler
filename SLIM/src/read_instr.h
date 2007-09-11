/*
 * read_instr.h - utility for reading intermediate sequence
 */

#ifndef READ_INSTR_H
#define READ_INSTR_H

/* I は BYTEへのポインタ */

#define LMN_IMS_READ_UINT8(X,I)  ((X)=*(uint8_t*)(I), I+=sizeof(uint8_t))
#define LMN_IMS_READ_UINT16(X,I) ((X)=*(uint16_t*)(I), I+=sizeof(uint16_t))
#define LMN_IMS_READ_UINT32(X,I) ((X)=*(uint32_t*)(I), I+=sizeof(uint32_t))
#define LMN_IMS_READ_DOUBLE(X,I) ((X)=*(double*)(I), I+=sizeof(double))

#define LMN_IMS_READ(T,X,I)      ((X)=*(T*)(I), I+=sizeof(T))

#define LMN_IMS_READ_SYMBOL(X,I) LMN_IMS_READ_UINT16(X,I)
#define LMN_IMS_RAED_OP(X,I)     ((X)=*(LmnInstrOp*)(I), I+=sizeof(LmnInstrOp))

#endif /* READ_INSTR_H */
