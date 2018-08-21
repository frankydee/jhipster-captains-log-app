export interface IELR {
    id?: number;
    elrCode?: string;
}

export class ELR implements IELR {
    constructor(public id?: number, public elrCode?: string) {}
}
