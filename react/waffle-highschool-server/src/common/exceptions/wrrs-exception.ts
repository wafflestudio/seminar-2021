import { HttpException, HttpStatus } from '@nestjs/common';

export class WrrsException extends HttpException {
  readonly errorCode: ErrorCode;

  constructor(errorCode: ErrorCode, message: string, statusCode: HttpStatus) {
    super(message, statusCode);
    this.errorCode = errorCode;
  }
}

export enum ErrorCode {
  // 데이터와 관련된 에러
  INVALID_GRADE = 30000,
  INVALID_NAME = 30001,
  DUPLICATED_STUDENT = 30002,
}
