export interface ITrack {
    id?: number;
    trackId?: string;
}

export class Track implements ITrack {
    constructor(public id?: number, public trackId?: string) {}
}
