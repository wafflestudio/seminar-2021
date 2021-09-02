import { HttpException, HttpStatus } from '@nestjs/common';
import { ApiProperty } from '@nestjs/swagger';

export class WrrsException extends HttpException {
  @ApiProperty({
    description: '에러 메세지',
    example: '올바르지 않은 ...입니다.',
  })
  readonly message: string;
  @ApiProperty({ description: '커스텀 에러 코드', example: 30001 })
  readonly errorCode: ErrorCode;

  constructor(errorCode: ErrorCode, message: string, statusCode: HttpStatus) {
    super({ message, errorCode }, statusCode);
  }
}

export enum ErrorCode {
  // 학생과 관련된 에러
  INVALID_GRADE = 30000,
  INVALID_NAME = 30001,
  DUPLICATED_STUDENT = 30002,
  ID_NOT_FOUND = 30003,
}
