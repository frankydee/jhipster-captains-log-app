import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrack } from 'app/shared/model/track.model';

type EntityResponseType = HttpResponse<ITrack>;
type EntityArrayResponseType = HttpResponse<ITrack[]>;

@Injectable({ providedIn: 'root' })
export class TrackService {
    private resourceUrl = SERVER_API_URL + 'api/tracks';

    constructor(private http: HttpClient) {}

    create(track: ITrack): Observable<EntityResponseType> {
        return this.http.post<ITrack>(this.resourceUrl, track, { observe: 'response' });
    }

    update(track: ITrack): Observable<EntityResponseType> {
        return this.http.put<ITrack>(this.resourceUrl, track, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITrack>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITrack[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
