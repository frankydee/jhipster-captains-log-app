import { Moment } from 'moment';
import { ILogEntry } from 'app/shared/model//log-entry.model';

export interface ICaptainsLog {
    id?: number;
    runDate?: Moment;
    rst?: string;
    loadDate?: Moment;
    logEntries?: ILogEntry[];
}

export class CaptainsLog implements ICaptainsLog {
    constructor(
        public id?: number,
        public runDate?: Moment,
        public rst?: string,
        public loadDate?: Moment,
        public logEntries?: ILogEntry[]
    ) {}
}
