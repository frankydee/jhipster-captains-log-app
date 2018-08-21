import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ELR } from 'app/shared/model/elr.model';
import { ELRService } from './elr.service';
import { ELRComponent } from './elr.component';
import { ELRDetailComponent } from './elr-detail.component';
import { ELRUpdateComponent } from './elr-update.component';
import { ELRDeletePopupComponent } from './elr-delete-dialog.component';
import { IELR } from 'app/shared/model/elr.model';

@Injectable({ providedIn: 'root' })
export class ELRResolve implements Resolve<IELR> {
    constructor(private service: ELRService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((eLR: HttpResponse<ELR>) => eLR.body));
        }
        return of(new ELR());
    }
}

export const eLRRoute: Routes = [
    {
        path: 'elr',
        component: ELRComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ELRS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elr/:id/view',
        component: ELRDetailComponent,
        resolve: {
            eLR: ELRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ELRS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elr/new',
        component: ELRUpdateComponent,
        resolve: {
            eLR: ELRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ELRS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elr/:id/edit',
        component: ELRUpdateComponent,
        resolve: {
            eLR: ELRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ELRS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eLRPopupRoute: Routes = [
    {
        path: 'elr/:id/delete',
        component: ELRDeletePopupComponent,
        resolve: {
            eLR: ELRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ELRS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
