import { ErrorCode, WrrsException } from '../common/exceptions/wrrs-exception';
import { HttpStatus } from '@nestjs/common';

export class DuplicatedStudentException extends WrrsException {
  constructor() {
    super(
      ErrorCode.DUPLICATED_STUDENT,
      '같은 학년에 같은 이름이 존재합니다.',
      HttpStatus.BAD_REQUEST,
    );
  }
}

export class InvalidGradeException extends WrrsException {
  constructor() {
    super(
      ErrorCode.INVALID_GRADE,
      '학년이 올바르지 않습니다.',
      HttpStatus.BAD_REQUEST,
    );
  }
}

export class InvalidNameException extends WrrsException {
  constructor() {
    super(
      ErrorCode.INVALID_NAME,
      '이름이 올바르지 않습니다.',
      HttpStatus.BAD_REQUEST,
    );
  }
}

export class IdNotFoundException extends WrrsException {
  constructor() {
    super(
      ErrorCode.ID_NOT_FOUND,
      '아이디가 존재하지 않습니다.',
      HttpStatus.BAD_REQUEST,
    );
  }
}

export class BadDataException extends WrrsException {
  constructor() {
    super(
      ErrorCode.BAD_DATA,
      '요청한 데이터가 올바르지 않습니다.',
      HttpStatus.BAD_REQUEST,
    );
  }
}
