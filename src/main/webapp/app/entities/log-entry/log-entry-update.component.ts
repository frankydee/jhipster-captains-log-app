import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILogEntry } from 'app/shared/model/log-entry.model';
import { LogEntryService } from './log-entry.service';
import { IELR } from 'app/shared/model/elr.model';
import { ELRService } from 'app/entities/elr';
import { ITrack } from 'app/shared/model/track.model';
import { TrackService } from 'app/entities/track';
import { ICaptainsLog } from 'app/shared/model/captains-log.model';
import { CaptainsLogService } from 'app/entities/captains-log';

@Component({
    selector: 'jhi-log-entry-update',
    templateUrl: './log-entry-update.component.html'
})
export class LogEntryUpdateComponent implements OnInit {
    private _logEntry: ILogEntry;
    isSaving: boolean;

    elrs: IELR[];

    trackids: ITrack[];

    captainslogs: ICaptainsLog[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private logEntryService: LogEntryService,
        private eLRService: ELRService,
        private trackService: TrackService,
        private captainsLogService: CaptainsLogService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ logEntry }) => {
            this.logEntry = logEntry;
        });
        this.eLRService.query({ filter: 'logentry-is-null' }).subscribe(
            (res: HttpResponse<IELR[]>) => {
                if (!this.logEntry.elr || !this.logEntry.elr.id) {
                    this.elrs = res.body;
                } else {
                    this.eLRService.find(this.logEntry.elr.id).subscribe(
                        (subRes: HttpResponse<IELR>) => {
                            this.elrs = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.trackService.query({ filter: 'logentry-is-null' }).subscribe(
            (res: HttpResponse<ITrack[]>) => {
                if (!this.logEntry.trackId || !this.logEntry.trackId.id) {
                    this.trackids = res.body;
                } else {
                    this.trackService.find(this.logEntry.trackId.id).subscribe(
                        (subRes: HttpResponse<ITrack>) => {
                            this.trackids = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.captainsLogService.query().subscribe(
            (res: HttpResponse<ICaptainsLog[]>) => {
                this.captainslogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.logEntry.id !== undefined) {
            this.subscribeToSaveResponse(this.logEntryService.update(this.logEntry));
        } else {
            this.subscribeToSaveResponse(this.logEntryService.create(this.logEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILogEntry>>) {
        result.subscribe((res: HttpResponse<ILogEntry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackELRById(index: number, item: IELR) {
        return item.id;
    }

    trackTrackById(index: number, item: ITrack) {
        return item.id;
    }

    trackCaptainsLogById(index: number, item: ICaptainsLog) {
        return item.id;
    }
    get logEntry() {
        return this._logEntry;
    }

    set logEntry(logEntry: ILogEntry) {
        this._logEntry = logEntry;
    }
}
