import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILogEntry } from 'app/shared/model/log-entry.model';

type EntityResponseType = HttpResponse<ILogEntry>;
type EntityArrayResponseType = HttpResponse<ILogEntry[]>;

@Injectable({ providedIn: 'root' })
export class LogEntryService {
    private resourceUrl = SERVER_API_URL + 'api/log-entries';

    constructor(private http: HttpClient) {}

    create(logEntry: ILogEntry): Observable<EntityResponseType> {
        return this.http.post<ILogEntry>(this.resourceUrl, logEntry, { observe: 'response' });
    }

    update(logEntry: ILogEntry): Observable<EntityResponseType> {
        return this.http.put<ILogEntry>(this.resourceUrl, logEntry, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILogEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILogEntry[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
