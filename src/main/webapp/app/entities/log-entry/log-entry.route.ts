import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LogEntry } from 'app/shared/model/log-entry.model';
import { LogEntryService } from './log-entry.service';
import { LogEntryComponent } from './log-entry.component';
import { LogEntryDetailComponent } from './log-entry-detail.component';
import { LogEntryUpdateComponent } from './log-entry-update.component';
import { LogEntryDeletePopupComponent } from './log-entry-delete-dialog.component';
import { ILogEntry } from 'app/shared/model/log-entry.model';

@Injectable({ providedIn: 'root' })
export class LogEntryResolve implements Resolve<ILogEntry> {
    constructor(private service: LogEntryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((logEntry: HttpResponse<LogEntry>) => logEntry.body));
        }
        return of(new LogEntry());
    }
}

export const logEntryRoute: Routes = [
    {
        path: 'log-entry',
        component: LogEntryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'log-entry/:id/view',
        component: LogEntryDetailComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'log-entry/new',
        component: LogEntryUpdateComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'log-entry/:id/edit',
        component: LogEntryUpdateComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogEntries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logEntryPopupRoute: Routes = [
    {
        path: 'log-entry/:id/delete',
        component: LogEntryDeletePopupComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogEntries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
