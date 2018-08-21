import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaptainsLog } from 'app/shared/model/captains-log.model';

type EntityResponseType = HttpResponse<ICaptainsLog>;
type EntityArrayResponseType = HttpResponse<ICaptainsLog[]>;

@Injectable({ providedIn: 'root' })
export class CaptainsLogService {
    private resourceUrl = SERVER_API_URL + 'api/captains-logs';

    constructor(private http: HttpClient) {}

    create(captainsLog: ICaptainsLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(captainsLog);
        return this.http
            .post<ICaptainsLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(captainsLog: ICaptainsLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(captainsLog);
        return this.http
            .put<ICaptainsLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICaptainsLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaptainsLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(captainsLog: ICaptainsLog): ICaptainsLog {
        const copy: ICaptainsLog = Object.assign({}, captainsLog, {
            runDate: captainsLog.runDate != null && captainsLog.runDate.isValid() ? captainsLog.runDate.toJSON() : null,
            loadDate: captainsLog.loadDate != null && captainsLog.loadDate.isValid() ? captainsLog.loadDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.runDate = res.body.runDate != null ? moment(res.body.runDate) : null;
        res.body.loadDate = res.body.loadDate != null ? moment(res.body.loadDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((captainsLog: ICaptainsLog) => {
            captainsLog.runDate = captainsLog.runDate != null ? moment(captainsLog.runDate) : null;
            captainsLog.loadDate = captainsLog.loadDate != null ? moment(captainsLog.loadDate) : null;
        });
        return res;
    }
}
