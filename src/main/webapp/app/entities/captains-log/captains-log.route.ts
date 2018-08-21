import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CaptainsLog } from 'app/shared/model/captains-log.model';
import { CaptainsLogService } from './captains-log.service';
import { CaptainsLogComponent } from './captains-log.component';
import { CaptainsLogDetailComponent } from './captains-log-detail.component';
import { CaptainsLogUpdateComponent } from './captains-log-update.component';
import { CaptainsLogDeletePopupComponent } from './captains-log-delete-dialog.component';
import { ICaptainsLog } from 'app/shared/model/captains-log.model';

@Injectable({ providedIn: 'root' })
export class CaptainsLogResolve implements Resolve<ICaptainsLog> {
    constructor(private service: CaptainsLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((captainsLog: HttpResponse<CaptainsLog>) => captainsLog.body));
        }
        return of(new CaptainsLog());
    }
}

export const captainsLogRoute: Routes = [
    {
        path: 'captains-log',
        component: CaptainsLogComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CaptainsLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'captains-log/:id/view',
        component: CaptainsLogDetailComponent,
        resolve: {
            captainsLog: CaptainsLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaptainsLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'captains-log/new',
        component: CaptainsLogUpdateComponent,
        resolve: {
            captainsLog: CaptainsLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaptainsLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'captains-log/:id/edit',
        component: CaptainsLogUpdateComponent,
        resolve: {
            captainsLog: CaptainsLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaptainsLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const captainsLogPopupRoute: Routes = [
    {
        path: 'captains-log/:id/delete',
        component: CaptainsLogDeletePopupComponent,
        resolve: {
            captainsLog: CaptainsLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaptainsLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
