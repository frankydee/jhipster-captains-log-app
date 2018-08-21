import { IELR } from 'app/shared/model//elr.model';
import { ITrack } from 'app/shared/model//track.model';
import { ICaptainsLog } from 'app/shared/model//captains-log.model';

export const enum InvalidationCode {
    OFF_ROUTE = 'OFF_ROUTE',
    INSTRUMENT_FAILURE = 'INSTRUMENT_FAILURE'
}

export interface ILogEntry {
    id?: number;
    startMileage?: number;
    endMileage?: number;
    invalidationCode?: InvalidationCode;
    elr?: IELR;
    trackId?: ITrack;
    captainsLog?: ICaptainsLog;
}

export class LogEntry implements ILogEntry {
    constructor(
        public id?: number,
        public startMileage?: number,
        public endMileage?: number,
        public invalidationCode?: InvalidationCode,
        public elr?: IELR,
        public trackId?: ITrack,
        public captainsLog?: ICaptainsLog
    ) {}
}
