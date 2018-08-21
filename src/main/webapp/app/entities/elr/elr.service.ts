import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IELR } from 'app/shared/model/elr.model';

type EntityResponseType = HttpResponse<IELR>;
type EntityArrayResponseType = HttpResponse<IELR[]>;

@Injectable({ providedIn: 'root' })
export class ELRService {
    private resourceUrl = SERVER_API_URL + 'api/elrs';

    constructor(private http: HttpClient) {}

    create(eLR: IELR): Observable<EntityResponseType> {
        return this.http.post<IELR>(this.resourceUrl, eLR, { observe: 'response' });
    }

    update(eLR: IELR): Observable<EntityResponseType> {
        return this.http.put<IELR>(this.resourceUrl, eLR, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IELR>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IELR[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
